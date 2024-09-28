import React from 'react';
import { Navigate } from 'react-router-dom'; // Assuming you're using v6

const ProtectedRoute = ({ children }) => {
    const isAuthenticated = true; // Example: Replace with actual authentication logic

    return isAuthenticated ? children : <Navigate to="/login" />;
};

export default ProtectedRoute;
