const router = require('express').Router()
const request = require('request')
const bodyParser = require('body-parser')

module.exports = (app) => {

    router.get('/', (req, res) => {
        res.send('Hello World')
    })

    router.get('/founds', 
    app.middlewares.redis,
    (req, res) => {
        request(`https://data.sncf.com/api/records/1.0/search/?dataset=objets-trouves-restitution&rows=100&q=date==${req.query.date}`,  (error, response, body) => {
            if(!error && response.statusCode == 200) {
                app.cache.redis.set(req.query.date, body, function(error, response) {
                    if(error) console.log(error);
                    res.send(body)                    
                })
            } else {
                console.log(`Error querying sncf data ${error}`);
                res.send(`${error}`)
            }
    
        })
    })

    router.get('/illustration', 
    app.middlewares.pixabayRedis,
    (req, res) => {
        request(`https://pixabay.com/api/?key=10072274-ed3795aa238afa6930012b8d0&q=${req.query.item}&image_type=illustration&pretty=true&lang=fr`,
         (error, response, data) => {
             if(error) {
                 res.status(500).send(error)
             } else {
                 app.cache.redis.set(req.query.item, data, function(err, response) {
                    if(error) {
                        console.log(`Error while caching pixabay data. Error is ${error}`)
                    } 
                     res.send(data)
                 })
             }
         })
    })

    router.post('/user',
        bodyParser.json(),
        app.actions.user.create
    )

    router.get('/user', app.actions.user.findById)

    router.get('/claims', app.actions.claim.findAll)
    router.get('/claim', app.actions.claim.findById)
    router.post('/claim', 
    bodyParser.json(),
    app.actions.claim.create)

    router.post('/record', 
    bodyParser.json(),
    app.actions.record.create)

    router.get('/record', app.actions.record.findById)
    
    return router

}