const router = require('express').Router();
const { getWeather } = require('../controllers/weather.controller');

router.get('/', getWeather);

module.exports = router;
