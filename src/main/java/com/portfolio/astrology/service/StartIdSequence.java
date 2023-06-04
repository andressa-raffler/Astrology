package com.portfolio.astrology.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

    public class StartIdSequence {

        private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
        private static final String USER = "postgres";
        private static final String PASS = "postgres";
        private static final String[] SEQUENCE_NAMES = {"user_seq", "house_seq", "planet_seq", "astrology_seq"};
        private static final int START_VALUE = 1;

        public static void start() {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
                for (String sequenceName : SEQUENCE_NAMES) {
                    // Checks if the sequence already exists
                    if (sequenceExists(conn, sequenceName)) {
                        System.out.println("The sequence " + sequenceName + " already exists in the database.");
                        continue;
                    }

                    // Creates the sequence
                    String createSequenceSql = "CREATE SEQUENCE " + sequenceName + " START " + START_VALUE;
                    try (PreparedStatement stmt = conn.prepareStatement(createSequenceSql)) {
                        stmt.executeUpdate();
                        System.out.println("Sequence " + sequenceName + " created successfully!");
                    } catch (SQLException e) {
                        System.err.println("Error creating the sequence " + sequenceName + ": " + e.getMessage());
                    }
                }

            } catch (SQLException e) {
                System.err.println("Error connecting to the database: " + e.getMessage());
            }
        }

        private static boolean sequenceExists(Connection conn, String sequenceName) throws SQLException {
            String checkSequenceSql = "SELECT * FROM information_schema.sequences WHERE sequence_name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(checkSequenceSql)) {
                stmt.setString(1, sequenceName);
                return stmt.executeQuery().next();
            }
        }

    }