import bcrypt from 'bcrypt';

// Simulate hashing the password before storing it
const hashedPassword1 = bcrypt.hashSync('password123', 10);  // Hashing the password 'password123'
const hashedPassword2 = bcrypt.hashSync('anotherPassword', 10); // Another password for demonstration

export const users = [
  {
    id: 1,
    username: 'test@gmail.com',
    password: hashedPassword1, // Hashed password 'password123'
    userType: 'Department' // Add the user type for validation
  },
  {
    id: 2,
    username: 'anotherUser@example.com',
    password: hashedPassword2, // Hashed password for another user
    userType: 'Hospital' // Add the user type for validation
  }
];
