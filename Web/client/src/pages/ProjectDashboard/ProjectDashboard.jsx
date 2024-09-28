import React, { useEffect, useState } from 'react';
import './ProjectDashboard.css'; // For styling
import Header from '../../components/Header/Header';
import { useParams } from 'react-router-dom'; // To get the project ID from the URL
import axios from 'axios';
import notFound_image from '../../assets/notFound.png';
import { useAuth } from '../../context/AuthContext';

const ProjectDashboard = () => {
    const { projectId } = useParams(); // Get the project ID from the route
    const [project, setProject] = useState(null); // State to hold project data
    const [volunteers, setVolunteers] = useState([]); // State to hold volunteers
    const [hospitals, setHospitals] = useState([]);

    const { user } = useAuth();

    // Fetch volunteers data
    const fetchVolunteersData = async () => {
        try {
            const response = await axios.get(`http://localhost:5000/api/volunteers/project/${projectId}`); // Adjust API endpoint as needed
            setVolunteers(response.data); // Set volunteer data
        } catch (error) {
            console.error('Error fetching volunteers data:', error);
        }
    };

    // Fetch project data from the API when component mounts
    useEffect(() => {
        const fetchProjectData = async () => {
            try {
                const response = await axios.get(`http://localhost:5000/api/projects/${projectId}`); // Fetch project by ID
                setProject(response.data); // Set project data
            } catch (error) {
                console.error('Error fetching project data:', error);
            }
        };

        const fetchHospitalsData = async () => {
            try {
                const response = await axios.get(`http://localhost:5000/api/projects/hospitals/${projectId}`);
                setHospitals(response.data); // Set hospitals data
            } catch (error) {
                console.error('Error fetching hospitals data:', error);
            }
        };

        fetchProjectData();
        fetchVolunteersData(); // Call this here to fetch volunteers when the component mounts
        fetchHospitalsData();
    }, [projectId]);

    const handleActAsVolunteer = async () => {
        // Logic to add entry in project_volunteers table
        const volunteerEmail = user.email; // Replace with the actual volunteer email from your context

        try {
            const response = await axios.post(`http://localhost:5000/api/volunteers/project_volunteers`, {
                projectId,
                volunteerEmail
            });
            console.log('Volunteer added:', response.data);
            // fetch the updated list of volunteers again
            fetchVolunteersData(); // Fetch volunteers again to update the table
        } catch (error) {
            console.error('Error adding volunteer:', error);
        }
    };

    // Check if project data is available before rendering
    if (!project) {
        return <div>Loading...</div>;
    }

    function fetchImage(id) {
        return "http://localhost:5000/uploads/" + id + ".jpg";
    }

    return (
        <div className="project-dashboard-container">
            <Header />
            <div className="project-dashboard">
                <div className="dashboard-top-portion">
                    <button className="emergency-button">Extreme Emergency</button>
                    <img
                        src={fetchImage(project.id)}
                        alt="Disaster"
                        className="dashboard-image"
                        onError={(e) => { e.target.src = notFound_image; }} // Handle missing image case
                    />
                    <div className="dashboard-content">
                        <h2 className="project-name">{project.title}</h2>
                        <p className="project-description">{project.description}</p>
                        <p className="affected-people">{project.approx_no_of_affected_people} people affected</p>
                        <div className="project-location">
                            <p>{project.address_line1}</p>
                            <p>{project.address_line2}</p>
                            <p>{project.state}, {project.pincode}</p>
                        </div>
                    </div>
                </div>

                {/* Donation Card */}
                {user.userType === 'Volunteer' && (
                    <div className="donation-card">
                        <h3>Do you want to help as a volunteer when needed?</h3>
                        <p><strong>Donations</strong>(optional): You can donate to PM National Relief Fund</p>
                        <p><strong>Blood</strong>: We might contact you for blood donations.</p>
                        <button className="donation-button" onClick={handleActAsVolunteer}>
                            Act as Volunteer
                        </button>
                    </div>
                )}

                {/* Volunteers Table */}
                <div className="volunteers-card">
                    <h3>Nearby Volunteers</h3>
                    <table className="volunteer-table">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Phone Number</th>
                                <th>Blood Group</th>
                            </tr>
                        </thead>
                        <tbody>
                            {volunteers.length > 0 ? (
                                volunteers.map((volunteer, index) => (
                                    <tr key={index}>
                                        <td>{volunteer.name}</td>
                                        <td>{volunteer.phone_number}</td>
                                        <td>{volunteer.blood_group}</td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan="3">No volunteers available</td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>

                <div className="hospitals-card">
                    <h3>Available Hospitals</h3>
                    <table className="hospital-table">
                        <thead>
                            <tr>
                                <th>Address Line 1</th>
                                <th>Address Line 2</th>
                                <th>Phone Number</th>
                                <th>No of Beds</th>
                                <th>Timings</th>
                            </tr>
                        </thead>
                        <tbody>
                            {hospitals.length > 0 ? (
                                hospitals.map((hospital, index) => (
                                    <tr key={index}>
                                        <td>{hospital.address_line1}</td>
                                        <td>{hospital.address_line2}</td>
                                        <td>{hospital.phone_number}</td>
                                        <td>{hospital.no_of_beds}</td>
                                        <td>{hospital.timings}</td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan="5">No hospitals available</td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default ProjectDashboard;