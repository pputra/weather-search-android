const request = require('request-promise');

module.exports = {
  getPhotos: async (keyword) => {
      try {
        const uri = `https://www.googleapis.com/customsearch/v1?q=${keyword}&cx=${process.env.SEARCH_ENGINE_ID}&imgSize=huge&imgType=news&num=8&searchType=image&key=${process.env.GOOGLE_API_KEY}`;

        const options = {
          uri,
          method: 'GET',
          json: true,
        };

        const { items } = await request(options);

        const photos = items.map((item) => item.link);

        return Promise.resolve(photos);
      } catch (err) {
        return Promise.reject(err);
      }
  },
};
