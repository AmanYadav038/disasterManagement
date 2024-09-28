import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext'; // Import useAuth for authentication context
import DarkBrandTitle from '../../components/BrandTitles/DarkBrandTitle';
import './LoginRegister.css';
import email_icon from '../../assets/email.png';
import password_icon from '../../assets/password.png';
import person_icon from '../../assets/person.png';
import other_icon from '../../assets/other.png';
import state_icon from '../../assets/state.png';
import phone_icon from '../../assets/phone.png';
import pincode_icon from '../../assets/pincode.png';
import time_icon from '../../assets/time.png';
import blood_icon from '../../assets/blood.png';
import RadioButtons from '../../components/RadioButtons';
import InputField from './InputField';
import SubmitButton from './SubmitButton';

const LoginRegister = () => {
    const [action, setAction] = useState('Login');
    const [selectedUserType, setSelectedUserType] = useState('Department');
    const [name, setName] = useState(''); // For name input during registration
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [statesOfOperation, setStatesOfOperation] = useState(''); // For Department registration
    const [noOfBeds, setNoOfBeds] = useState(''); // For Hospital registration
    const [timings, setTimings] = useState(''); // For Hospital registration
    const [addressLine1, setAddressLine1] = useState(''); // For Hospital registration
    const [addressLine2, setAddressLine2] = useState(''); // For Hospital registration
    const [state, setState] = useState(''); // For Hospital registration
    const [pincode, setPincode] = useState(''); // For Hospital registration
    const [phone, setPhone] = useState(''); // For Hospital registration
    const [bloodGroup, setBloodGroup] = useState('');

    const navigate = useNavigate();
    const { login } = useAuth(); // Get the login function from auth context

    // Handle the login request
    const handleLogin = async () => {
        try {
            const response = await fetch('http://localhost:5000/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, password, selectedUserType }),
                credentials: 'include',
            });

            const result = await response.json();

            if (response.ok) {
                console.log('Login successful', result.user);
                login(result.user); // Call the login function to update auth state
                navigate('/active-projects'); // Redirect after login
            } else {
                console.error('Login failed', result.message);
                alert(`Login failed: ${result.message}`);
            }
        } catch (error) {
            console.error('Error during login:', error);
        }
    };

    const handleRegister = async () => {
        try {
            // Construct the registration data including selectedUserType
            const registrationData = {
                selectedUserType,
                email,
                name,
                password,
                statesOfOperation,
                noOfBeds,
                timings,
                addressLine1,
                addressLine2,
                state,
                pincode,
                phone,
                bloodGroup              
            };

            const response = await fetch('http://localhost:5000/api/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(registrationData),
                credentials: 'include',
            });

            const result = await response.json();

            if (response.ok) {
                console.log('Registration successful', result.user);
                // Redirect or perform further actions on success
                navigate('/active-projects');
            } else {
                console.error('Registration failed', result.message);
                alert(`Registration failed: ${result.message}`);
            }
        } catch (error) {
            console.error('Error during registration:', error);
        }
    };

    return (
        <div className="screen">
            <div className="login-container">
                <DarkBrandTitle />
                <div className="login-header">
                    <div className="login-heading">{action}</div>
                    <div className="login-head-underline"></div>
                </div>

                <div className="inputs">
                    <RadioButtons
                        options={[
                            { value: 'Department', label: 'Department', id: 'department' },
                            { value: 'Hospital', label: 'Hospital', id: 'hospital' },
                            { value: 'ERT', label: 'ERT', id: 'ert' },
                            { value: 'Volunteer', label: 'Volunteer', id: 'volunteer' }
                        ]}
                        selectedValue={selectedUserType}
                        onChange={(e) => setSelectedUserType(e.target.value)}
                    />

                    {/* Show Name field for Department or Volunteer during Registration */}
                    {action === 'Register' && (selectedUserType === 'Department' || selectedUserType === 'Volunteer') && (
                        <InputField
                            type="text"
                            placeholder="Name"
                            icon={person_icon} 
                            onChange={(e) => setName(e.target.value)}
                        />
                    )}

                    {/* Show States of Operation for Department */}
                    {action === 'Register' && selectedUserType === 'Department' && (
                        <InputField
                            type="text"
                            placeholder="States of Operation"
                            icon={state_icon}
                            onChange={(e) => setStatesOfOperation(e.target.value)}
                        />
                    )}

                    {/* Show fields for Hospital during Registration */}
                    {action === 'Register' && selectedUserType === 'Hospital' && (
                        <>
                            <InputField
                                type="text"
                                placeholder="Number of Beds"
                                icon={other_icon}
                                onChange={(e) => setNoOfBeds(e.target.value)}
                            />
                            <InputField
                                type="text"
                                placeholder="Timings"
                                icon={time_icon}
                                onChange={(e) => setTimings(e.target.value)}
                            />
                        </>
                    )}

                    {action === 'Register' && (selectedUserType === 'Hospital' || selectedUserType === 'Volunteer') && (
                        <>
                            <InputField
                                type="text"
                                placeholder="Name & Address Line 1"
                                icon={other_icon}
                                onChange={(e) => setAddressLine1(e.target.value)}
                            />
                            <InputField
                                type="text"
                                placeholder="Address Line 2"
                                icon={other_icon}
                                onChange={(e) => setAddressLine2(e.target.value)}
                            />
                            <InputField
                                type="text"
                                placeholder="State"
                                icon={state_icon}
                                onChange={(e) => setState(e.target.value)}
                            />
                            <InputField
                                type="text"
                                placeholder="Pincode"
                                icon={pincode_icon}
                                onChange={(e) => setPincode(e.target.value)}
                            />
                            <InputField
                                type="text"
                                placeholder="Phone Number"
                                icon={phone_icon}
                                onChange={(e) => setPhone(e.target.value)}
                            />
                        </>
                    )}

                    {action === 'Register' && selectedUserType === 'Volunteer' && (
                        <InputField
                            type="text"
                            placeholder="Blood Group"
                            icon={blood_icon}
                            onChange={(e) => setBloodGroup(e.target.value)}
                        />
                    )}

                    <InputField
                        type="email"
                        placeholder="Email id"
                        icon={email_icon}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <InputField
                        type="password"
                        placeholder="Password"
                        icon={password_icon}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>

                {action === 'Login' && (
                    <div className="forgot-password">
                        Forgot Password? <span>Click here!</span>
                    </div>
                )}

                <SubmitButton
                    action={action}
                    setAction={setAction}
                    onClick={action === 'Login' ? handleLogin : handleRegister} // Handle Login or Register based on action
                />
            </div>

            <div className="banner"></div>
        </div>
    );
};

export default LoginRegister;