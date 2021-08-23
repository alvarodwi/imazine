import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:get/route_manager.dart';
import 'package:imazine/screens/home_screen.dart';
import 'package:url_launcher/url_launcher.dart';

class LoginScreen extends StatelessWidget {
  const LoginScreen({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
      ),
      body: ListView(
        children: [
          Center(
            child: Text(
              'IMAZINE',
              style: TextStyle(
                fontFamily: 'strasua',
                fontSize: 50,
                color: Colors.amber,
                fontWeight: FontWeight.w600,
              ),
            ),
          ),
          MyCustomForm(),
        ],
      ),
    );
  }
}

class MyCustomForm extends StatefulWidget {
  @override
  MyCustomFormState createState() {
    return MyCustomFormState();
  }
}

class MyCustomFormState extends State<MyCustomForm> {
  String _npm;
  String _password;

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: EdgeInsets.symmetric(horizontal: 32, vertical: 16),
          child: TextFormField(
            style: TextStyle(
              fontFamily: 'OpenSans',
            ),
            decoration: InputDecoration(
              border: UnderlineInputBorder(),
              labelText: "NPM",
              labelStyle: TextStyle(
                fontFamily: 'OpenSans',
              ),
            ),
            onChanged: (text) {
              setState(() {
                _npm = text;
              });
            },
          ),
        ),
        Padding(
          padding: EdgeInsets.symmetric(horizontal: 32, vertical: 16),
          child: TextFormField(
            obscureText: true,
            style: TextStyle(
              fontFamily: 'OpenSans',
            ),
            decoration: InputDecoration(
              border: UnderlineInputBorder(),
              labelText: "Password",
              labelStyle: TextStyle(
                fontFamily: 'OpenSans',
              ),
            ),
            onChanged: (text) {
              setState(() {
                _password = text;
              });
            },
          ),
        ),
        Padding(
          padding: EdgeInsets.symmetric(horizontal: 32, vertical: 16),
          child: ElevatedButton(
            child: Text('Login'),
            onPressed: () {
              if (_npm == "12345" && _password == "test") {
                Get.to(() => HomeScreen());
              }
            },
          ),
        ),
      ],
    );
  }
}
