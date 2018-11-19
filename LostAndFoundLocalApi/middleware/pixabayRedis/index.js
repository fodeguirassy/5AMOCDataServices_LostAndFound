module.exports = (app) => {

    return function(req, res, next) {
        let item = req.query.item
        app.cache.redis.get(item, function(error, reply){
            if(error) {
                console.log(`Error while retrieving values for key ${item} And error is ${error}`);
                return next()
            } else if(reply == null || reply.toString().length <= 0){
                return next()
            }else {
                return res.send(reply)
            }
        })

    }

}