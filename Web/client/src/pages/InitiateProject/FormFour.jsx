// client/src/pages/InitiateProject/FormFour.jsx
import React, { useContext, useState } from 'react';
import AppContext from './Context';
import './InitiateProject.css';
import { useAuth } from '../../context/AuthContext';

const FormFour = () => {
    const myContext = useContext(AppContext);
    const { projectDetails } = myContext;
    const { 
        projectName, 
        projectDescription, 
        affectedPeople, 
        projectAdd1, 
        projectAdd2, 
        pincode, 
        projectState, 
        imageFile, 
        setStep, 
        currentPage 
    } = projectDetails;

    const { user } = useAuth();
    const initiator = user.email;

    const [loading, setLoading] = useState(false); // Loading state for "Please wait"

    // Handle form submission and project insertion
    const handleSubmit = async () => {
        setLoading(true);
        const projectData = {
            projectName,
            projectDescription,
            projectAdd1,
            projectAdd2,
            pincode,
            projectState,
            affectedPeople,
            initiator
        };

        console.log('Submitting project data:', projectData);

        try {
            // Send project data to the server
            const response = await fetch('http://localhost:5000/api/projects', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(projectData),
            });

            console.log('Response status:', response.status);

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`Server error: ${errorText}`);
            }

            const data = await response.json();
            console.log('Project submission result:', data);

            if (data.success) {
                const projectId = data.projectId;
                console.log('Project ID received:', projectId);
                await uploadImage(projectId); // Call image upload function
            } else {
                console.error('Failed to insert project:', data.message);
                setLoading(false);
            }
        } catch (err) {
            console.error('Error submitting project:', err);
            setLoading(false);
        }
    };

    // Handle image upload after project insertion
    const uploadImage = async (projectId) => {
        const formData = new FormData();
        formData.append('image', imageFile);
        formData.append('projectId', projectId);

        console.log('Uploading image with projectId:', projectId);

        try {
            const response = await fetch('http://localhost:5000/api/projects/upload-image', { // Corrected endpoint
                method: 'POST',
                body: formData,
            });

            console.log('Image upload response status:', response.status);

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`Image upload failed: ${errorText}`);
            }

            const data = await response.json();
            console.log('Image upload result:', data);

            if (data.success) {
                console.log('Image upload successful, moving to next step.');
                setStep(currentPage + 1); // Move to the next step (FormFinish)
            } else {
                console.error('Image upload failed:', data.message);
                setLoading(false);
            }
        } catch (err) {
            console.error('Error uploading image:', err);
            setLoading(false);
        }
    };

    const prev = () => {
        setStep(currentPage - 1);
    };

    return (
        <div className="reviewContainer">
            <h2>Review Your Project Details</h2>
            <div className="form-container">
                {/* Display all form details */}
                <div className="form-item">
                    <label className='projectLabel'>Project Name:</label>
                    <span className="project-detail">{projectName}</span>
                </div>
                <div className="form-item">
                    <label className='projectLabel'>Project Description:</label>
                    <span className="project-detail">{projectDescription}</span>
                </div>
                <div className="form-item">
                    <label className='projectLabel'>Address Line 1:</label>
                    <span className="project-detail">{projectAdd1}</span>
                </div>
                <div className="form-item">
                    <label className='projectLabel'>Address Line 2:</label>
                    <span className="project-detail">{projectAdd2}</span>
                </div>
                <div className="form-item">
                    <label className='projectLabel'>State:</label>
                    <span className="project-detail">{projectState}</span>
                </div>
                <div className="form-item">
                    <label className='projectLabel'>Pincode:</label>
                    <span className="project-detail">{pincode}</span>
                </div>
                <div className="form-item">
                    <label className='projectLabel'>Estimated no. of affected people:</label>
                    <span className="project-detail">{affectedPeople}</span>
                </div>

                {/* Image Preview */}
                {imageFile && (
                    <div className="image-preview">
                        <h4>Uploaded Image:</h4>
                        <img 
                            src={URL.createObjectURL(imageFile)} 
                            alt="Preview" 
                            className="image-preview-box" 
                            style={{ width: '300px', height: 'auto' }} 
                        />
                    </div>
                )}

                {/* Loading Indicator */}
                {loading && <p>Please wait...</p>}

                {/* Submit and Previous Buttons */}
                {!loading && (
                    <>
                        <button className="formSubmit" onClick={handleSubmit}>Submit</button>
                        <button type="button" className="formSubmit gray" onClick={prev}>Previous</button>
                    </>
                )}
            </div>
        </div>
    );
};

export default FormFour;
