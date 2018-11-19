module.exports = (app) => {

    console.log('Loading Actions');

    app.actions = {
        user : require('./user')(app),
        claim : require('./claim')(app),
        record : require('./record')(app)
    }

}