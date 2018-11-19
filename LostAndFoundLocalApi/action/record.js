module.exports = (app) => {

    const Record = app.models.Record
    const User = app.models.User
    const Claim = app.models.Claim

    function create(req, res) {
        Record.create(req.body)
        .then( (record) => {
            process.send({ cmd: 'notifyRequest', procId : process.pid });
            res.send(record)
        }).catch( (err) => {
            res.status(500).send(err)
        })
    }

    function findById(req, res){
        Record.findOne({ where : { recordId : req.query.recordId} }).then( (record) => {
            res.status(200).send(record)
        })
    }

    return {
        create,
        findById
    }

}