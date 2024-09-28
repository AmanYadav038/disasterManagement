import React, { useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import LoginRegister from './pages/LoginRegister/LoginRegister';
import ActiveProjects from './pages/ActiveProjects/ActiveProjects';
import HomePage from "./pages/HomePage/HomePage";
import InitiateProject from "./pages/InitiateProject/InitiateProject";
import ProjectDashboard from './pages/ProjectDashboard/ProjectDashboard';

const App = () => {
    return (
        <AuthProvider>
            <Router>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/login" element={<LoginRegister />} />
                    <Route 
                        path="/active-projects" 
                        element={
                            <PrivateRoute>
                                <ActiveProjects/>
                            </PrivateRoute>
                        } 
                    />
                    <Route path="/initiate-project" element={<PrivateRoute><InitiateProject /></PrivateRoute>   } />
                    <Route path="/project-dashboard/:projectId" element={<PrivateRoute><ProjectDashboard /></PrivateRoute>   } />
                </Routes>
            </Router>
        </AuthProvider>
    );
};

// PrivateRoute component to protect routes
const PrivateRoute = ({ children }) => {
    const { user } = useAuth();

    // Add effect to trigger alert box when the page is refreshed or closed
    useEffect(() => {
        const handleBeforeUnload = (e) => {
            if (user) {
                const message = "Are you sure you want to leave? Your session might be lost.";
                e.preventDefault();  // Some browsers require this for custom messages
                e.returnValue = message;  // Standard behavior for most browsers
                return message;
            }
        };

        window.addEventListener("beforeunload", handleBeforeUnload);

        return () => {
            window.removeEventListener("beforeunload", handleBeforeUnload);
        };
    }, [user]);

    return user ? children : <Navigate to="/login" />;
};

export default App;
