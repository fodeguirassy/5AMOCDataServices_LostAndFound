const randomString = require('randomstring')
module.exports = (app) => {

    const User = app.models.User
    const Claim = app.models.Claim
    const Record = app.models.Record

    app.sequelize.sync().then( () => {

        Claim.create({
            description : randomString.generate({lenght : 20, charset : 'alphabetic'})
        })

        Record.create({
            recordId : randomString.generate(6),
            gc_obo_nature_c : randomString.generate(6),
            gc_obo_gare_origine_r_name : randomString.generate(6),
            date : randomString.generate(6),
            gc_obo_type_c : randomString.generate(6),
            illustrationUri : randomString.generate(16)
        })

        User.create({
            userId : randomString.generate(6),
            email : randomString.generate({lenght : 7, charset : 'alphabetic'}),
            password : randomString.generate(6),
            displayName : randomString.generate(6),
            photoUrl : randomString.generate(6)
        }).then( (user) => {
            Claim.create( { description :  randomString.generate({lenght : 20, charset : 'alphabetic'})} ).then( (claim) => {
                user.addClaim(claim)
                claim.setUser(user)

                Record.create({
                    recordId : randomString.generate(6),
                    gc_obo_nature_c : randomString.generate(6),
                    gc_obo_gare_origine_r_name : randomString.generate(6),
                    date : randomString.generate(6),
                    gc_obo_type_c : randomString.generate(6),
                    illustrationUri : randomString.generate(16)
                }).then( (record) => {
                    claim.setRecord(record)
                })
            })    
        })
    })
}