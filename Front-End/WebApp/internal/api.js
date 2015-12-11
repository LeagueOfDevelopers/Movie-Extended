var unirest = require('unirest');

var BASE_URI = 'localhost:3000';

module.exports = {
    Companies: Companies,
    Cinemas: Cinemas
};

function Companies(method, data, callback) {
    var url = BASE_URI;
    console.log(data);
    switch (method) {
        case 'post': {
            url += '/Companies';
            unirest.post(url)
                .headers({'Content-Type': 'application/json'})
                .send(data)
                .end(function (response) {
                    var bodyResponse = response.body;
                    if (!bodyResponse) {
                        return callback(new Error('The resource is not reached.'));
                    }
                    callback(null, bodyResponse);
                });
            break;
        }
        case 'get': {
            url += '/Companies';
            if (data && data.company_guid) {
                url += '/' + data.company_guid;
            }
            unirest.get(url)
                .end(function (response) {
                    var bodyResponse = response.body;
                    if (!bodyResponse) {
                        return callback(new Error('The resource is not reached.'));
                    }
                    console.log(bodyResponse);
                    callback(null, bodyResponse);
                });
            break;
        }
    }
}

function Cinemas(method, data, callback) {
    var url = BASE_URI;
    console.log(data);
    switch (method) {
        case 'post': {
            url += '/Companies/' + data.company_guid + '/Cinemas';
            unirest.post(url)
                .headers({'Content-Type': 'application/json'})
                .send(data.data)
                .end(function (response) {
                    var bodyResponse = response.body;
                    if (!bodyResponse) {
                        return callback(new Error('The resource is not reached.'));
                    }
                    callback(null, bodyResponse);
                });
            break;
        }
        case 'get': {
            url += '/Companies/' + data.company_guid + '/Cinemas';
            if (data.cinema_guid) {
                url += '/' + data.ciname_guid;
            }
            unirest.get(url)
                .end(function (response) {
                    var bodyResponse = response.body;
                    if (!bodyResponse) {
                        return callback(new Error('The resource is not reached.'));
                    }
                    console.log(bodyResponse);
                    callback(null, bodyResponse);
                });
            break;
        }
    }
}
