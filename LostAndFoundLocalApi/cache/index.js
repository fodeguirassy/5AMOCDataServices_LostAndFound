module.exports = (app) => {

    console.log('Loading cache');
    
    app.cache = {
        redis : require('./redis')(app)
    }
}