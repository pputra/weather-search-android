const { getCoordinateByFullLocation } = require('../actions/geocoding.action');
const { getWeatherByCoordinate } = require('../actions/weather.action');
const { getPhotos } = require('../actions/searchEngine.action');

module.exports = {
  getWeather: async (req, res) => {
    try {
      const {
        lat,
        lon,
        location,
        photos_keyword: photosKeyword,
      } = req.query;

  
      let weatherData;

      if (lat && lon) {
        weatherData = await getWeatherByCoordinate(lat, lon);
      } else {
        const { lat: latitude, lon: longitude } = await getCoordinateByFullLocation(location);
        weatherData = await getWeatherByCoordinate(latitude, longitude);
      }

      if (photosKeyword) {
        const photos = await getPhotos(photosKeyword);
      
        return res.status(200).json({
          message: "weather data has been fetched",
          photos,
          weatherData,
        });
      }

      return res.status(200).json({
        message: "weather data has been fetched",
        weatherData,
      });
    } catch (err) {

      return res.status(401).json({ message: 'Invalid Address.' });
    }
  },
};
