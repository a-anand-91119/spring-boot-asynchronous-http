import http from 'k6/http'
import {check, sleep} from 'k6'

export const options = {
    stages:[
        { target: 500, duration: '30s' },
        { target: 1000, duration: '30s' },
        { target: 1000, duration: '2m' },
        { target: 500, duration: '30s' },
        { target: 0, duration: '30s' },
    ]
};

export default function () {
    let name = randomChars(5)
    let res = http.get(`http://localhost:8080/api/v1/request/send?name=${name}`)

    check(res, {'success': (r) => r.status === 200})

    sleep(0.3)
}

function randomChars(len) {
    let chars = '';
    while (chars.length < len) {
        chars += Math.random().toString(36).substring(2);
    }
    return chars.substring(0, len);
}