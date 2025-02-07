package com.islamux.mothaker

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlarmManager.*
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scheduleHourlyNotification(this)

        setContent {
            HourlyNotificationTheme { // Use a defined theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HourlyNotificationApp()
                }
            }
        }
    }
}


@SuppressLint("ScheduleExactAlarm")
fun scheduleHourlyNotification(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationReceiver::class.java) // Create a BroadcastReceiver
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT) // Use appropriate flags

    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        add(Calendar.HOUR_OF_DAY, 1) // Schedule for the next hour
        set(Calendar.MINUTE, 0) // Set minutes to 0 for hourly precision
        set(Calendar.SECOND, 0)
    }

    alarmManager.setExactAndAllowWhileIdle( // Use setExactAndAllowWhileIdle for reliability
        RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )
}

@Composable
fun HourlyNotificationApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Notification description",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        // Add a button to test the notification immediately (for debugging)
        val context = LocalContext.current
        Button(onClick = {
            // Trigger the notification directly for testing
            val intent = Intent(context, NotificationReceiver::class.java)
            context.sendBroadcast(intent)
        }) {
            Text("Test Notification")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HourlyNotificationTheme {
        HourlyNotificationApp()
    }
}

// Define your theme (even if it's just the default for now)
@Composable
fun HourlyNotificationTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        content = content
    )
}

// package com.islamux.mothaker

// import android.os.Bundle
// import androidx.activity.ComponentActivity
// import androidx.activity.compose.setContent
// import androidx.compose.foundation.layout.*
// import androidx.compose.material3.*
// import androidx.compose.runtime.Composable
// import androidx.compose.ui.Alignment
// import androidx.compose.ui.Modifier
// import androidx.compose.ui.res.stringResource
// import androidx.compose.ui.tooling.preview.Preview
// import androidx.compose.ui.unit.dp

// class MainActivity : ComponentActivity() {
//     override fun onCreate(savedInstanceState: Bundle?) {
//         super.onCreate(savedInstanceState)

//         // Schedule the hourly notification
//         scheduleHourlyNotification(this)

//         // Set up Jetpack Compose UI
//         setContent {
//             HourlyNotificationTheme {
//                 Surface(
//                     modifier = Modifier.fillMaxSize(),
//                     color = MaterialTheme.colorScheme.background
//                 ) {
//                     HourlyNotificationApp()
//                 }
//             }
//         }
//     }
// }

// @Composable
// fun HourlyNotificationApp() {
//     Column(
//         modifier = Modifier
//             .fillMaxSize()
//             .padding(16.dp),
//         verticalArrangement = Arrangement.Center,
//         horizontalAlignment = Alignment.CenterHorizontally
//     ) {
//         Text(
//             text = stringResource(R.string.app_name),
//             style = MaterialTheme.typography.headlineMedium,
//             color = MaterialTheme.colorScheme.primary
//         )
//         Spacer(modifier = Modifier.height(16.dp))
//         Text(
//             text = stringResource(R.string.notification_description),
//             style = MaterialTheme.typography.bodyMedium,
//             color = MaterialTheme.colorScheme.onBackground
//         )
//     }
// }

// @Preview(showBackground = true)
// @Composable
// fun DefaultPreview() {
//     HourlyNotificationTheme {
//         HourlyNotificationApp()
//     }
// }




// package com.islamux.mothaker

// import android.os.Bundle
// import androidx.activity.ComponentActivity
// import androidx.activity.compose.setContent
// import androidx.activity.enableEdgeToEdge
// import androidx.compose.foundation.layout.fillMaxSize
// import androidx.compose.foundation.layout.padding
// import androidx.compose.material3.Scaffold
// import androidx.compose.material3.Text
// import androidx.compose.runtime.Composable
// import androidx.compose.ui.Modifier
// import androidx.compose.ui.tooling.preview.Preview
// import com.islamux.mothaker.ui.theme.MothakerTheme

// class MainActivity : ComponentActivity() {
//     override fun onCreate(savedInstanceState: Bundle?) {
//         super.onCreate(savedInstanceState)
//         enableEdgeToEdge()
//         setContent {
//             MothakerTheme {
//                 Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                     Greeting(
//                         name = "Android",
//                         modifier = Modifier.padding(innerPadding)
//                     )
//                 }
//             }
//         }
//     }
// }

// @Composable
// fun Greeting(name: String, modifier: Modifier = Modifier) {
//     Text(
//         text = "Hello $name!",
//         modifier = modifier
//     )
// }

// @Preview(showBackground = true)
// @Composable
// fun GreetingPreview() {
//     MothakerTheme {
//         Greeting("Android")
//     }
// }