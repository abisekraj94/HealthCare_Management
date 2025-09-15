-- 1. User
CREATE TABLE miot."user" (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    phone VARCHAR(20),
    profile_picture_url VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Role
CREATE TABLE miot.role (
    role_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

-- 3. Role_Permission
CREATE TABLE miot.role_permission (
    permission_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

-- 4. User_Role (Mapping)
CREATE TABLE miot.user_role (
    user_role_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES "user"(user_id) ON DELETE CASCADE,
    role_id INTEGER NOT NULL REFERENCES role(role_id) ON DELETE CASCADE,
    UNIQUE(user_id, role_id)
);

-- 5. Role_Permission_Map (Mapping)
CREATE TABLE miot.role_permission_map (
    role_permission_map_id SERIAL PRIMARY KEY,
    role_id INTEGER NOT NULL REFERENCES role(role_id) ON DELETE CASCADE,
    permission_id INTEGER NOT NULL REFERENCES role_permission(permission_id) ON DELETE CASCADE,
    UNIQUE(role_id, permission_id)
);

-- 6. Patient
CREATE TABLE miot.patient (
    patient_id SERIAL PRIMARY KEY,
    mrn VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    dob DATE,
    gender VARCHAR(20),
    phone VARCHAR(20),
    email VARCHAR(255),
    address TEXT,
    primary_physician_id INTEGER REFERENCES "user"(user_id),
    referral_physician_id INTEGER REFERENCES "user"(user_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 7. Patient_Identifier
CREATE TABLE miot.patient_identifier (
    identifier_id SERIAL PRIMARY KEY,
    patient_id INTEGER NOT NULL REFERENCES patient(patient_id) ON DELETE CASCADE,
    identifier_type VARCHAR(50) NOT NULL,
    identifier_value VARCHAR(100) NOT NULL
);

-- 8. Patient_Vital_Sign
CREATE TABLE miot.patient_vital_sign (
    vital_sign_id SERIAL PRIMARY KEY,
    patient_id INTEGER NOT NULL REFERENCES patient(patient_id) ON DELETE CASCADE,
    recorded_at TIMESTAMP NOT NULL,
    temperature NUMERIC(5,2),
    blood_pressure VARCHAR(20),
    heart_rate INTEGER,
    respiratory_rate INTEGER,
    notes TEXT
);

-- 9. Patient_Medical_History
CREATE TABLE miot.patient_medical_history (
    history_id SERIAL PRIMARY KEY,
    patient_id INTEGER NOT NULL REFERENCES patient(patient_id) ON DELETE CASCADE,
    condition VARCHAR(100) NOT NULL,
    description TEXT,
    diagnosed_at DATE,
    resolved_at DATE
);

-- 10. User_Patient_Assignment
CREATE TABLE miot.user_patient_assignment (
    assignment_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES "user"(user_id) ON DELETE CASCADE,
    patient_id INTEGER NOT NULL REFERENCES patient(patient_id) ON DELETE CASCADE,
    assignment_type VARCHAR(50),
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    unassigned_at TIMESTAMP
);

-- 11. Patient_Visit
CREATE TABLE miot.patient_visit (
    visit_id SERIAL PRIMARY KEY,
    patient_id INTEGER NOT NULL REFERENCES patient(patient_id) ON DELETE CASCADE,
    visit_date TIMESTAMP NOT NULL,
    visit_type VARCHAR(50),
    physician_id INTEGER REFERENCES "user"(user_id),
    notes TEXT
);

-- 12. Visit_Patient_Diagnosis
CREATE TABLE miot.visit_patient_diagnosis (
    diagnosis_id SERIAL PRIMARY KEY,
    visit_id INTEGER NOT NULL REFERENCES patient_visit(visit_id) ON DELETE CASCADE,
    diagnosis_code VARCHAR(50),
    diagnosis_description TEXT,
    diagnosed_by INTEGER REFERENCES "user"(user_id),
    diagnosed_at TIMESTAMP
);

-- 13. Visit_Patient_Allergy
CREATE TABLE miot.visit_patient_allergy (
    allergy_id SERIAL PRIMARY KEY,
    visit_id INTEGER NOT NULL REFERENCES patient_visit(visit_id) ON DELETE CASCADE,
    allergy_name VARCHAR(100),
    reaction VARCHAR(100),
    severity VARCHAR(50),
    notes TEXT
);

-- 14. Visit_Patient_Medication
CREATE TABLE miot.visit_patient_medication (
    medication_id SERIAL PRIMARY KEY,
    visit_id INTEGER NOT NULL REFERENCES patient_visit(visit_id) ON DELETE CASCADE,
    medication_name VARCHAR(100),
    dosage VARCHAR(50),
    frequency VARCHAR(50),
    route VARCHAR(50),
    start_date DATE,
    end_date DATE,
    prescribed_by INTEGER REFERENCES "user"(user_id)
);

-- 15. Document_Management
CREATE TABLE miot.document_management (
    document_id SERIAL PRIMARY KEY,
    patient_id INTEGER REFERENCES patient(patient_id) ON DELETE CASCADE,
    visit_id INTEGER REFERENCES patient_visit(visit_id) ON DELETE CASCADE,
    file_name VARCHAR(255) NOT NULL,
    file_type VARCHAR(50),
    file_url VARCHAR(255) NOT NULL,
    uploaded_by INTEGER REFERENCES "user"(user_id),
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description TEXT
);

