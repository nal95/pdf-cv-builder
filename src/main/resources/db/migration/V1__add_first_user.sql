INSERT INTO USERS (relevant_experience_years,
                   first_name,
                   last_name,
                   password,
                   email,
                   nationality,
                   location,
                   summary,
                   objectives,
                   profession,
                   mobile_phone_number,
                   image_path,
                   data)
VALUES (5,
        'John',
        'Doe',
        'securePassword',
        'john.doe@example.com',
        'US',
        'New York',
        'Experienced software engineer',
        'Seeking challenging opportunities',
        'Software Engineer',
        '+1 123-456-7890',
        'images/nal95.png',
        '{
            "networks": [
                {"option": "LINKEDIN", "link": "https://linkedin.com/in/johndoe", "referenceName": "John Doe"},
                {"option": "GIT", "link": "https://github.com/johndoe", "referenceName": "johndoe"}
            ],
            "educations": [
                {"school": "University of XYZ", "field": "Computer Science", "degree": "Bachelor''s", "startDate": "2020-01-01", "endDate": "2024-01-01", "duration": 4, "summary": "Graduated with honors"}
            ],
            "workExperiences": [
                {"company": "ABC Inc.", "companyCity": "New York", "companyLink": "https://abcinc.com", "occupiedPosition": "Software Engineer", "startDate": "2024-02-01", "endDate": "2025-02-01", "duration": 1, "summary": "Developed cutting-edge software"}
            ],
            "technicalExperiences": [
                {"topic": "PROGRAMMING", "details": [{"name": "Java", "level": 5}, {"name": "JavaScript", "level": 4}]}
            ],
            "tools": ["Git", "Jira", "Webpack", "TDD", "MVC"],
            "methodologies": ["Agile", "Scrum"],
            "skills": ["SQL", "Firebase", "Algorithm"]
        }' FORMAT JSON
       );
