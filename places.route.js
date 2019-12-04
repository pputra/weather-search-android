const router = require('express').Router();
const { getPlacesSuggestions } = require('../controllers/places.controller');

router.get('/', getPlacesSuggestions);

module.exports = router;
