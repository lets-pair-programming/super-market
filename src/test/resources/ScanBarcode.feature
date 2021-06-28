Feature: sanity check

  Scenario: ping
    When I 'GET' the api '/ping'
    Then the server response will match '{ data: "pong" }'
