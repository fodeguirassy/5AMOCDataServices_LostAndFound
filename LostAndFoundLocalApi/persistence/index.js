const Sequelize = require('sequelize')

module.exports = (app) => {
    
    console.log('Loading persistence');
    
    app.sequelize =  new Sequelize('lostandfounds', 'root', 'fofo', {
        host : 'localhost',
        dialect : 'mysql',
        pool : {
            max : 100,
            min : 0,
            acquie : 30000,
            idle : 10000
        },
        logging: false
    })
}