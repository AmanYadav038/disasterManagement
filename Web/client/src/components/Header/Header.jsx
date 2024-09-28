import React from 'react';
import { useAuth } from '../../context/AuthContext';
import BrandLogoTitleDark from '../BrandLogoTitle/BrandLogoTitleDark';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'; // Import FontAwesomeIcon
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons'; // Import the logout icon
import './Header.css';
import { useNavigate } from 'react-router-dom';

function Header() {
  const { logout } = useAuth();  // Get the logout function from the context
  const navigate = useNavigate();

  return (
    <div className="navbar">
      <BrandLogoTitleDark />
      <button className="logout-button" onClick={()=>{logout && navigate('/login')}}>
        <FontAwesomeIcon icon={faSignOutAlt} />
      </button>
    </div>
  );
}

export default Header;
