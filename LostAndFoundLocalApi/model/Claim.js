const Sequelize = require('sequelize')
module.exports = (app) => {

    return  app.sequelize.define('claim', {
        
        claimId : {
            type : Sequelize.INTEGER,
            primaryKey: true,
            autoIncrement: true 
        },

        description : {
            type : Sequelize.STRING
        },

        date : {
            type : Sequelize.STRING,
        },

        status : {
            type : Sequelize.ENUM,
            values : ['accepted', 'pending', 'rejected'],
            allowNull : false,
            defaultValue : 'pending'
        }
    })

}