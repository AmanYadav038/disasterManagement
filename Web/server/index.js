// server/index.js
import express from 'express';
import cors from 'cors';
import authRoutes from './routes/authRoutes.js'; // Ensure this exists or remove if not used
import projectRoutes from './routes/projectRoutes.js'; // Import the project routes
import volunteerRoutes from './routes/volunteerRoutes.js'; // Import the volunteer routes
import db from './db.js'; // Database connection
import path from 'path';
import { fileURLToPath } from 'url';

// For ES Modules to use __dirname
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const app = express();
const PORT = process.env.PORT || 5000;

// CORS configuration
app.use(cors({ origin: 'http://localhost:3000', credentials: true }));
app.use(express.json());

// Serve static files from 'uploads' directory
app.use('/uploads', express.static(path.join(__dirname, 'uploads')));

// Routes
app.use('/api/auth', authRoutes); // Ensure authRoutes.js exists or remove if not needed
app.use('/api/projects', projectRoutes); // Use the project routes
app.use('/api/volunteers', volunteerRoutes);


// Fallback route for undefined endpoints
app.use((req, res, next) => {
    res.status(404).json({ success: false, message: 'Endpoint not found' });
});

// Error-handling middleware
app.use((err, req, res, next) => {
    console.error('Unhandled error:', err);
    res.status(500).json({ success: false, message: 'Internal Server Error' });
});

// Start server
app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
});
