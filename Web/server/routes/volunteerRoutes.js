// server/routes/volunteerRoutes.js
import express from 'express';
import db from '../db.js';

const router = express.Router();

// Get volunteers associated with a project by project ID
router.get('/project/:projectId', async (req, res) => {
    const { projectId } = req.params;

    try {
        const query = `
            SELECT v.name, v.phone_number, v.blood_group 
            FROM volunteer v
            INNER JOIN project_volunteers pv ON v.email = pv.volunteer_email
            WHERE pv.id = $1;
        `;
        const result = await db.query(query, [projectId]);
        res.json(result.rows);
    } catch (error) {
        console.error('Error fetching volunteers:', error);
        res.status(500).json({ error: 'Server error' });
    }
});

// Add a volunteer to a project
router.post('/project_volunteers', async (req, res) => {
    const { projectId, volunteerEmail } = req.body;

    if (!projectId || !volunteerEmail) {
        return res.status(400).json({ message: 'Project ID and volunteer email are required' });
    }

    try {
        const query = `
            INSERT INTO project_volunteers (id, volunteer_email) 
            VALUES ($1, $2)
            ON CONFLICT (id, volunteer_email) DO NOTHING; -- Prevent duplicates
        `;
        await db.query(query, [projectId, volunteerEmail]);
        res.json({ message: 'Volunteer added successfully' });
    } catch (error) {
        console.error('Error adding volunteer:', error);
        res.status(500).json({ error: 'Server error' });
    }
});

export default router;
