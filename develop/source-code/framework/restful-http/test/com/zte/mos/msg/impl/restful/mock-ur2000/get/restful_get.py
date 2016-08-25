from flask import Flask, jsonify, request, Response, json

app = Flask(__name__)

result = {
    "inventory" : {
    "e000001": {
        "guid": "e000001",
        "name": "e000001",
        "ext0_name": "ext0_name",
        "ext1_name": "ext1_name",
        "serial_number": "serial_number",
        "mac_address": "aa:aa:aa:aa:aa:aa",
        "product_code": "product_code",
        "build_number": "build_number",
        "build_version": "build_version",
        "software_version": "software_version",
        "radio_frequencies": "radio_frequencies",
        "latitude": "latitude",
        "longitude": "longitude"
    },
    "e000002": {
            "guid": "e000002",
            "name": "e000002",
            "ext0_name": "ext0_name",
            "ext1_name": "ext1_name",
            "serial_number": "serial_number",
            "mac_address": "aa:aa:aa:aa:aa:aa",
            "product_code": "product_code",
            "build_number": "build_number",
            "build_version": "build_version",
            "software_version": "software_version",
            "radio_frequencies": "radio_frequencies",
            "latitude": "latitude",
            "longitude": "longitude"
        }
    },
    "event_history": [
        {
            "source": "e000001",
            "id": "node_ems_state",
            "type": "alarm",
            "severity": 10,
            "description": "description",
            "time": "time"
        }
    ]
}


@app.route("/net/net-name/inventory")
def get_inventory():
    return jsonify(result.get("inventory"))


@app.route("/net/net-name/event_history")
def get_history():
    if request.args.get("period") == "3600":
        return Response(json.dumps(result.get("event_history")))
    else:
        return ""

if __name__ == "__main__":
    app.run("127.0.0.1", 8091, debug=True)
