const { getPlaces } = require('../actions/places.action');

module.exports = {
  getPlacesSuggestions: async (req, res) => {
    try {
      const {
        input,
      } = req.query;
  
      const places = await getPlaces(input);

      res.status(200).json({
        message: 'places suggestions have been fetched',
        places,
      });
    } catch (err) {
      res.status(401).json({
        message: err.message,
      });
    }
  }
};
