import db from '../db.js';
import fs from 'fs';
import path from 'path';

export const createProject = async (req, res) => {
    const { title, description, approx_no_of_affected_people, address_line1, address_line2, state, pincode } = req.body;

    try {
        // Insert project details into the database
        const result = await db.query(
            'INSERT INTO project (title, description, approx_no_of_affected_people, address_line1, address_line2, state, pincode) VALUES ($1, $2, $3, $4, $5, $6, $7) RETURNING id',
            [title, description, approx_no_of_affected_people, address_line1, address_line2, state, pincode]
        );

        const projectId = result.rows[0].id;

        // Rename the image file to match the project ID
        const oldPath = path.join('uploads', 'temp.jpg');
        const newPath = path.join('uploads', `${projectId}.jpg`);
        fs.renameSync(oldPath, newPath);

        res.status(201).json({ message: 'Project created successfully', projectId });
    } catch (error) {
        console.error('Error inserting project:', error);
        res.status(500).json({ message: 'Error creating project' });
    }
};
