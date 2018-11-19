module.exports = (app) => {

    console.log('Loading models');
    
    app.models = {
        User : require('./user')(app),
        Claim : require('./claim')(app),
        Record : require('./record')(app)
    }
    
    app.models.User.belongsToMany(app.models.Claim, { through : 'userClaims' })
    app.models.Claim.belongsTo(app.models.User)
    app.models.Claim.hasOne(app.models.Record)
    app.models.Record.belongsToMany(app.models.Claim, { through : 'recordClaims' } )

}