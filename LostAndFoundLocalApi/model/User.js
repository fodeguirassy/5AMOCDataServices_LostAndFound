const Sequelize = require('sequelize')

module.exports = (app) => {

    console.log('Loading User');
    
    return app.sequelize.define('user', {

        userId : {
            type : Sequelize.STRING
        },
        email : {
            type : Sequelize.STRING
        },
        password : {
            type : Sequelize.STRING
        },
        displayName : {
            type : Sequelize.STRING
        },
        photoUrl : {
            type : Sequelize.STRING
        },
        isAdmin : {
            type : Sequelize.BOOLEAN,
            allowNull : false,
            defaultValue : false
        }
    })

}