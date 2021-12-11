Link Shortener
It is also necessary to develop an http service that generates short links.

Description

The following functionality should be implemented in the service:
1. The
Resource/generate link generator must process a post request containing the original
link and generate a short link. The original link is passed in the body
Request MESSAGE in the "original" field of the JSON object. The generated short link is
passed in the response body in the “link" field of the JSON object. Generated short
the link must have the format /l/{some short name}, that is, it must not contain
the server address, where some -short-name is the link identifier. The algorithm for generating
this identifier and its format remains at the discretion of the author.
Request example:
PUBLISH /Create
Example of the request body:
{
"original”: “https://some-server.com/some/url?some_param=1 ”
}
Response example:
{
"link": "/l/some short name"
}
2. Redirect
The resource /l/{some short name} should redirect to the original url.
Request Path Parameters:
- some - short-name - link id
Request example:
GET /l/some-short-name
We suggest thinking about how you can optimize the work with links
that have a lot of clicks.
3. Statistics
3.1 Statistics on specific links The
resource/statistics/{some-short name} must process the RECEIVE request and return
statistics of clicks on a specific link.
Request Path Parameters:
- some - short-name - link id
The service response must contain a JSON object with the following fields:
- link - generated short link
- original - original link
- rank - the place of the link in the top queries
- count it... number of short link requests
Request example:
GET /statistics/some -short-name
Response example:
{
"link”: "/l/some short name”,
"original”: “http://some-server.com/some/url ”
"rank”: 1,
"quantity": 100500
}
3.2 Link rating
The resource /statistics should process the make-request and return query statistics
sorted by the frequency of requests in descending order and the possibility
of pagination.
Query string parameters:
- page - page number
- count - the number of records displayed on the page, the maximum possible
value is 100 (inclusive)
The service response must contain an array of JSON object data described in
Section 3.1 Statistics on specific links.
Request example:
GET /statistics?page=1 and quantity=2
Response example:
[
{
"link”: "/l/some short name”,
"original”: “http://some-server.com/some/url ”
"rank”: 1,
"quantity": 100500
},
{
"link": "/l/some other short name”,
"original”: “http://another-server.com/some/url ”
"rank”: 2,
"quantity”: 40000
}]
