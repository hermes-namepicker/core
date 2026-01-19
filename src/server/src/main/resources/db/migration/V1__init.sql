-- Flyway V1 migration: create Classes and Students tables
-- Uses SQL Server syntax

-- Note: the database is created/configured by Docker Compose (see compose.yaml). We only create tables here.
-- Create Classes table
IF OBJECT_ID(N'dbo.Classes', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Classes (
        uid UNIQUEIDENTIFIER NOT NULL DEFAULT NEWID(),
        className NVARCHAR(100) NOT NULL,
        CONSTRAINT PK_Classes PRIMARY KEY (uid)
    );
END

-- Create Students table
IF OBJECT_ID(N'dbo.Students', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Students (
        uid UNIQUEIDENTIFIER NOT NULL DEFAULT NEWID(),
        name NVARCHAR(100) NOT NULL,
        class_id UNIQUEIDENTIFIER NULL,
        CONSTRAINT PK_Students PRIMARY KEY (uid),
        CONSTRAINT FK_Students_Classes FOREIGN KEY (class_id) REFERENCES dbo.Classes(uid)
    );
END
