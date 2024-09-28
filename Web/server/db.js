import pg from "pg";

const db = new pg.Client({
    user: "postgres",
    host: "localhost",
    database: "disaster_management",
    password: "postgres",
    port: 5432,
});

db.connect()
    .then(() => console.log("Connected to the database"))
    .catch(err => console.error("Connection error", err.stack));

export default db;