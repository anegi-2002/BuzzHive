CREATE SCHEMA buzzhive_schema;
CREATE TABLE buzzhive_schema.posts (
id SERIAL PRIMARY KEY,
    post_id  VARCHAR(255) UNIQUE NOT NULL,         -- Unique identifier for each post
    user_id VARCHAR(255) NOT NULL,      -- ID of the user who created the post
    text_content TEXT NOT NULL,         -- The content of the post
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- Timestamp for when the post was created
);
CREATE TABLE buzzhive_schema.images (
    id SERIAL PRIMARY KEY,        -- Unique identifier for each image
    post_id VARCHAR(255) NOT NULL,               -- Foreign key linking to the posts table
    image_path TEXT NOT NULL,            -- The URL or path to the image
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp for when the image was uploaded
    FOREIGN KEY (post_id) REFERENCES buzzhive_schema.posts (post_id) ON DELETE CASCADE -- Ensure images are deleted when the post is deleted
);