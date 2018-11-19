module.exports = (app) => {

    return function(req, res, next) {
        console.log(`date param is ${req.query.date}`)
        let item = req.query.date

        app.cache.redis.get(req.query.date, function(error, reply){
            if(error) {
                console.log(`Error while retrieving values for key ${item} And error is ${error}`);
                return next()
            } else if(reply == null || reply.toString().length <= 0){
                console.log('Reply is empty or null');
                return next()
            }else {
                return res.send(reply)
            }
        })
    }
}