import express from 'express';
import bcrypt from 'bcrypt';
import db from '../db.js';

const router = express.Router();

// Helper function to map user types to corresponding table names
function tableName(str) {
    switch (str) {
        case "Department": return "departments";
        case "Hospital": return "hospitals";
        case "Volunteer": return "volunteer";
        case "ERT": return "ert";
        default: throw new Error('Invalid user type');
    }
}

// Register a new user based on the selectedUserType
router.post('/register', async (req, res) => {
    const { selectedUserType,
            email,
            name,
            password,
            statesOfOperation,  // For Department
            noOfBeds,           // For Hospital
            timings,            // For Hospital
            addressLine1,       // For Hospital
            addressLine2,       // For Hospital
            state,              // For Hospital
            pincode,            // For Hospital
            phone,
            bloodGroup } = req.body;

    console.log("Selected User Type:", selectedUserType);  // Add this line for debugging

    try {
        const hashedPassword = await bcrypt.hash(password, 10);
        
        // Prepare common query parameters
        let query = '';
        let queryParams = [];

        // Switch case based on selectedUserType to construct the query and its parameters
        switch (selectedUserType) {
            case "Department":
                query = 'INSERT INTO departments (email, name, password, state_of_operation) VALUES ($1, $2, $3, $4) RETURNING *';
                queryParams = [email, name, hashedPassword, statesOfOperation];
                break;
            
            case "Hospital":
                query = 'INSERT INTO hospitals (email, password, no_of_beds, timings, address_line1, address_line2, state, pincode, phone_number) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9) RETURNING *';
                queryParams = [email, hashedPassword, noOfBeds, timings, addressLine1, addressLine2, state, pincode, phone];
                break;

            case "Volunteer":
                query = 'INSERT INTO volunteer (email, name, password, address_line1, address_line2, state, pincode, phone_number, blood_group) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9) RETURNING *';
                queryParams = [email, name, hashedPassword, addressLine1, addressLine2, state, pincode, phone, bloodGroup];
                break;

            case "ERT":
                query = 'INSERT INTO ert (email, password) VALUES ($1, $2) RETURNING *';
                queryParams = [email, hashedPassword];
                break;

            default:
                return res.status(400).json({ message: 'Invalid user type' });
        }

        // Execute the query
        const result = await db.query(query, queryParams);
        res.status(201).json({ user: result.rows[0] });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Server error' });
    }
});


// Login a user
router.post('/login', async (req, res) => {
    const { email, password, selectedUserType } = req.body;

    try {
        const query = `SELECT * FROM ${tableName(selectedUserType)} WHERE email = $1`;
        const result = await db.query(query, [email]);
        const user = result.rows[0];

        if (!user || !(await bcrypt.compare(password, user.password))) {
            return res.status(403).json({ message: 'Invalid user type, email or password' });
        }

        res.status(200).json({ user: {userType: selectedUserType, email: user.email, name: user.name } });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Server error' });
    }
});

export default router;
