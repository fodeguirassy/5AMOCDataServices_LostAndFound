const Sequelize = require('sequelize')

module.exports = (app) => {

    return app.sequelize.define('record', {

        recordId : { type : Sequelize.STRING },
        gc_obo_nature_c : { type : Sequelize.STRING },
        gc_obo_gare_origine_r_name : { type : Sequelize.STRING },
        date : { type : Sequelize.STRING },
        gc_obo_type_c : { type : Sequelize.STRING },
        illustrationUri : { type : Sequelize.STRING }
    })

}