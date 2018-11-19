module.exports = (app) => {

    const User = app.models.User
    const Claim = app.models.Claim
    const Record = app.models.Record

    function create(req, res) {

        User.findOne( { where : { userId : req.query.userId } } ).then( (user) => {
            Claim.create(req.body).then( (claim) => {
                user.addClaim(claim)
                claim.setUser(user)
                
                Record.findOne( { where : { recordId : req.query.recordId } } ).then( (record) => {
                    claim.setRecord(record)
                    record.addClaim(claim)
                    res.status(200).send(claim)
                })

            })
        }).catch( (error) => {
                res.status(500).send(error)
        })
    }

    function findAll(req, res) {
        Claim.findAll({ include : [ User, Record ] })
        .then((claims) => {
            res.send(claims)
        }).catch( (err) => {
            res.status(500).send(err)
        })
    }

    function findById(req, res) {
        Claim.findOne( { where : { claimId : req.query.claimId }, include : [ User, Record ] } )
        .then( (claim) => {
            res.send(claim)
        }).catch( (error) => {
            res.status(500).send(error)
        })
    }

    return {
        create,
        findAll,
        findById
    }

}