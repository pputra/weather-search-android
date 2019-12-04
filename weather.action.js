const request = require('request-promise');

module.exports = {
  getWeatherByCoordinate: async (lat, lon) => {
    try {
      const uri = `https://api.darksky.net/forecast/${process.env.DARK_SKY_API_KEY}/${lat},${lon}`;

      const options = {
        uri,
        method: 'GET',
        json: true,
      };

      const weatherData = await request(options);

      return Promise.resolve(weatherData);
    } catch (err) {
      return Promise.reject(err);
    }
  },
  getWeatherByCoordinateAndTime: async (lat, lon, time) => {
    try {
      const uri = `https://api.darksky.net/forecast/${process.env.DARK_SKY_API_KEY}/${lat},${lon},${time}`;

      const options = {
        uri,
        method: 'GET',
        json: true,
      };

      const weatherData = await request(options);

      return Promise.resolve(weatherData);
    } catch (err) {
      return Promise.reject(err);
    }
  }
};
