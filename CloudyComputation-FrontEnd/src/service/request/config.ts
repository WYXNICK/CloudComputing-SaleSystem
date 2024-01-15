let BASE_URL: string;

if (process.env.NODE_ENV === 'development') {
    BASE_URL = 'http://localhost:5555';
} else if (process.env.NODE_ENV === 'production') {
    BASE_URL = 'http://localhost:5555';
} else {
    BASE_URL = 'http://localhost:5555';
}

export { BASE_URL };
