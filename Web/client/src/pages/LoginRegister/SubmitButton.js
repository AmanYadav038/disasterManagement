import React from 'react';

const SubmitButton = ({ action, setAction, onClick }) => (
    <div className="submit-container">
        {/* Main Submit Button (Login or Register based on current action) */}
        <div className="submit" onClick={onClick}>  {/* Bind onClick to the main action button */}
            {action}
        </div>

        {/* Toggle between Login and Register */}
        <div className='submit gray' onClick={() => { action === "Register" ? setAction("Login") : setAction("Register") }}>
            {action === 'Register' ? 'Login' : 'Register'}
        </div>
    </div>
);

export default SubmitButton;
