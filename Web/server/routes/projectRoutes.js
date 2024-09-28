// server/routes/projectRoutes.js
import express, { Router } from 'express';
import multer from 'multer';
import path from 'path';
import db from '../db.js';
import fs from 'fs';

const router = express.Router();

// Ensure 'uploads' folder exists
const uploadsDir = path.join(process.cwd(), 'uploads');
if (!fs.existsSync(uploadsDir)) {
    fs.mkdirSync(uploadsDir);
    console.log('Created uploads directory.');
}

// Configure multer for file uploads
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, 'uploads'); // Directory to save uploaded files
    },
    filename: (req, file, cb) => {
        const projectId = req.body.projectId;
        const ext = path.extname(file.originalname);
        cb(null, `${projectId}${ext}`); // Rename file to 'projectId.extension' (e.g., 1.jpg)
    }
});

const upload = multer({ storage });

// Get all projects
router.get('/', async (req, res) => {
    try {
        const result = await db.query('SELECT * FROM projects'); // Adjust the query as needed
        res.json(result.rows); // Send the projects data as response
    } catch (err) {
        console.error('Error fetching projects:', err);
        res.status(500).json({ message: 'Error fetching projects' });
    }
});


router.get('/:projectId', async (req, res) => {
    const { projectId } = req.params;
    
    try {
        const project = await db.query('SELECT * FROM projects WHERE id = $1', [projectId]);
        console.log('Project Data:', project.rows[0]); // Debug the fetched project data

        if (project.rows.length === 0) {
            return res.status(404).json({ error: 'Project not found' });
        }

        res.json(project.rows[0]);
    } catch (error) {
        console.error('Error fetching project:', error);
        res.status(500).json({ error: 'Server error' });
    }
});


// New route to fetch hospitals based on project pincode
router.get('/hospitals/:projectId', async (req, res) => {
    const { projectId } = req.params;

    try {
        // Fetch the project's pincode
        const projectResult = await db.query('SELECT pincode FROM projects WHERE id = $1', [projectId]);
        if (projectResult.rows.length === 0) {
            return res.status(404).json({ message: 'Project not found' });
        }

        const projectPincode = projectResult.rows[0].pincode;

        // Fetch hospitals that match the project's pincode
        const hospitalsResult = await db.query(
            'SELECT address_line1, address_line2, phone_number, no_of_beds, timings FROM hospitals WHERE pincode = $1',
            [projectPincode]
        );

        res.json(hospitalsResult.rows);
    } catch (error) {
        console.error('Error fetching hospitals:', error);
        res.status(500).json({ message: 'Error fetching hospitals' });
    }
});



// Route to insert project details
router.post('/', async (req, res) => {
    const { projectName, projectDescription, projectAdd1, projectAdd2, pincode, projectState, affectedPeople, initiator} = req.body;

    console.log('Received project data:', req.body);

    try {
        const query = `
            INSERT INTO projects (title, description, approx_no_of_affected_people, address_line1, address_line2, state, initiator, pincode)
            VALUES ($1, $2, $3, $4, $5, $6, $7, $8)
            RETURNING id;
        `;

        const values = [projectName, projectDescription, affectedPeople, projectAdd1, projectAdd2, projectState, initiator, pincode];
        const result = await db.query(query, values);

        const projectId = result.rows[0].id;
        console.log('Project inserted with ID:', projectId);

        res.json({ success: true, projectId });
    } catch (error) {
        console.error('Error inserting project:', error);
        res.status(500).json({ success: false, message: 'Error inserting project' });
    }
});

// Route to handle image upload
// Inside projectRoutes.js (Upload image route)

router.post('/upload-image', upload.single('image'), (req, res) => {
    const projectId = req.body.projectId;

    if (!req.file || !projectId) {
        return res.status(400).json({ success: false, message: 'Missing file or project ID' });
    }

    // Get the file extension (optional if you want to keep original file type, otherwise force .jpg)
    const extension = '.jpg'; // Always saving as .jpg
    const newFilename = `${projectId}${extension}`;
    const destinationPath = `uploads/${newFilename}`;

    // Rename the uploaded file to match projectId.jpg
    fs.rename(req.file.path, destinationPath, (err) => {
        if (err) {
            console.error('Error renaming file:', err);
            return res.status(500).json({ success: false, message: 'Error saving image' });
        }
        res.json({ success: true, message: 'Image uploaded successfully' });
    });
});


export default router;
