const router = require('express').Router();
const weather = require('./weather.route');
const places = require('./places.route');

router.get('/', (req, res) => {
  res.status(200).json('home');
});

router.use('/weather', weather);
router.use('/places', places);

module.exports = router;
