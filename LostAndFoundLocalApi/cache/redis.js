const redis = require('redis')

module.exports = (app) => {

    console.log('Loading Redis');
    let client = redis.createClient()
    client.on("error", function(error) {
        console.log(`Redis Error is ${error}`);
    })

    return client
    
}