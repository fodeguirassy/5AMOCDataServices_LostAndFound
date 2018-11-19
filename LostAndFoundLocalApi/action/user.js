module.exports = (app) => {

    const User = app.models.User
    const Claim = app.models.Claim
    const Record = app.models.Record

    function create(req, res) {
        User.create(req.body)
        .then( (user) => {
           res.send(user)
        }).catch( (error) => {
            res.status(500).send(error)
        })
    }

    function findById(req, res) {
        User.findAll( { where : { userId : req.query.userId }, include : [ { model : Claim , include : [ Record ] } ]} )
        .then( (users) => {
            res.send(users[0])
        }).catch( (err) => {
            res.status(404).send(err)
        })
    }

    return {
        create,
        findById
    }

}