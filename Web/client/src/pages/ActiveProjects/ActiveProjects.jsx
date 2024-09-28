import React, { useEffect, useState } from 'react';
import './ActiveProjects.css';
import Header from '../../components/Header/Header';
import { useNavigate } from "react-router-dom";
import { useAuth } from '../../context/AuthContext';
import axios from 'axios';
import notFound_image from '../../assets/notFound.png';

const ActiveProjects = () => {
    const [projects, setProjects] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [pincodeSearch, setPincodeSearch] = useState('');
    const [selectedSearchType, setSelectedSearchType] = useState('pincode');
    const [isSearchDisabled, setIsSearchDisabled] = useState(false);

    const { user } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProjects = async () => {
            try {
                const response = await axios.get('http://localhost:5000/api/projects');
                console.log("Projects fetched:", response.data);
                setProjects(response.data);
            } catch (error) {
                console.error("Error fetching projects:", error);
            }
        };

        fetchProjects();
    }, []);

    const handleSearch = (e) => {
        setSearchTerm(e.target.value);
    };

    const handlePincodeSearch = (e) => {
        setPincodeSearch(e.target.value);
    };

    const handleSearchTypeChange = (e) => {
        const value = e.target.value;
        setSelectedSearchType(value);

        if (value === 'allActive' || value === 'allCompleted') {
            setIsSearchDisabled(true);
            setSearchTerm('');
            setPincodeSearch('');
        } else {
            setIsSearchDisabled(false);
        }
    };

    const filteredProjects = projects.filter(project => {
        if (!project) return false;
        if (selectedSearchType === 'pincode') {
            return project.pincode?.includes(pincodeSearch) || false;
        } else if (selectedSearchType === 'projectName') {
            return project.title?.toLowerCase().includes(searchTerm.toLowerCase()) || false;
        } else if (selectedSearchType === 'state') {
            return project.state?.toLowerCase().includes(searchTerm.toLowerCase()) || false;
        } else if (selectedSearchType === 'allActive') {
            return project.status === 'active';
        } else if (selectedSearchType === 'allCompleted') {
            return project.status === 'completed';
        }
        return true;
    });

    const handleProjectClick = (projectId) => {
        navigate(`/project-dashboard/${projectId}`); // Navigate to the project dashboard with the project ID
    };

    function fetchImage(id){
        return "http://localhost:5000/uploads/"+id+".jpg";
    }

    return (
        <div className="active-project-container">
            <Header />
            <div className="active-projects">
                <div className="search-section">
                    <select className="dropdown" value={selectedSearchType} onChange={handleSearchTypeChange}>
                        <option value="pincode">Search by Pincode</option>
                        <option value="projectName">Search by Project Name</option>
                        <option value="state">Search by State</option>
                        <option value="allActive">View All Active Projects</option>
                        <option value="allCompleted">View All Completed Projects</option>
                    </select>
                    <input 
                        type="text" 
                        placeholder={
                            selectedSearchType === 'pincode' 
                            ? 'Enter Pincode' 
                            : selectedSearchType === 'projectName' 
                            ? 'Enter Project Name'
                            : selectedSearchType === 'state' 
                            ? 'Enter State' 
                            : ''
                        } 
                        value={selectedSearchType === 'pincode' ? pincodeSearch : searchTerm} 
                        onChange={selectedSearchType === 'pincode' ? handlePincodeSearch : handleSearch} 
                        className="search-box"
                        disabled={isSearchDisabled}
                    />
                    {user.userType === 'Department' && (
                        <button className="initiate-project-btn" onClick={() => navigate('/initiate-project')}>
                            Initiate Project
                        </button>
                    )}
                </div>

                <div className="projects-grid">
                    {filteredProjects.length > 0 ? (
                        filteredProjects.map(project => (
                            <div 
                                key={project.id} 
                                className="project-card" 
                                onClick={() => handleProjectClick(project.id)} // Call handleProjectClick on card click
                            >
                                <img src={fetchImage(project.id)} alt="Disaster" className="project-image" />
                                <h3 className="project-title">{project.title}</h3>
                                <p className="project-state">State/UT: {project.state}</p>
                                <p className="project-pincode">Pincode: {project.pincode}</p>
                                <p className="affected-people">{project.approx_no_of_affected_people} people affected</p>
                                <p className="project-status">Status: {project.status}</p>
                            </div>
                        ))
                    ) : (
                        <div className="not-found-container">
                            <p className="not-found-text">No projects found matching your search criteria.</p>
                            <img src={notFound_image} alt="Not Found" className="not-found-image" />
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default ActiveProjects;
