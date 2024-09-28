import passport from 'passport';
import { Strategy as LocalStrategy } from 'passport-local';
import bcrypt from 'bcryptjs';
import { users } from '../data/users.js'; // Temporary users array

passport.use(
  new LocalStrategy((username, password, done) => {
    const user = users.find(user => user.username === username);
    if (!user) {
      return done(null, false, { message: 'Incorrect username' });
    }

    // Compare passwords using bcrypt
    bcrypt.compare(password, user.password, (err, isMatch) => {
      if (err) throw err;
      if (isMatch) {
        return done(null, user);
      } else {
        return done(null, false, { message: 'Incorrect password' });
      }
    });
  })
);

// Serialize user into session
passport.serializeUser((user, done) => {
  done(null, user.username);
});

// Deserialize user from session
passport.deserializeUser((username, done) => {
  const user = users.find(user => user.username === username);
  done(null, user);
});
