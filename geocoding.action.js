const request = require('request-promise');

module.exports = {
  getCoordinateByFullAddress: async (street, city, state) => {
    try {
      const uri = `https://maps.googleapis.com/maps/api/geocode/json?address=${street},${city},
        ${state}&key=${process.env.GOOGLE_API_KEY}`;

      const options = {
        uri,
        method: 'GET',
        json: true,
      };

      const geocodeData = await request(options);

      const { lat, lng } = geocodeData.results[0].geometry.location;

      const coordinate = {
        lat,
        lon: lng,
      };

      return Promise.resolve(coordinate);
    } catch (err) {
      return Promise.reject(err);
    }
  },
  getCoordinateByFullLocation: async (location) => {
    try {
      const uri = `https://maps.googleapis.com/maps/api/geocode/json?address=${location}&key=${process.env.GOOGLE_API_KEY}`;

      const options = {
        uri,
        method: 'GET',
        json: true,
      };

      const geocodeData = await request(options);

      const { lat, lng } = geocodeData.results[0].geometry.location;

      const coordinate = {
        lat,
        lon: lng,
      };

      return Promise.resolve(coordinate);
    } catch (err) {
      return Promise.reject(err);
    }
  }
};
