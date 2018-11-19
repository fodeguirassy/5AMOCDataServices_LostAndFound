module.exports = (app) => {

    console.log('Loading middlewares');

    app.middlewares = {
        redis : require('./redis')(app),
        pixabayRedis : require('./pixabayRedis')(app)
    }

}