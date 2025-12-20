package dev.pace.circleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight


// Circle Area Calculation
private fun calculateCircleArea(radius: Double): Double {
    return Math.PI * radius * radius
}

private fun exitApp(activity: ComponentActivity) {
    activity.finishAffinity()
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            CircleArea()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CircleArea() {
    var radiusInput by rememberSaveable { mutableStateOf("") }
    var area by rememberSaveable { mutableStateOf<Double?>(null) }

    val activity = LocalContext.current as ComponentActivity

    Scaffold(

        //Title of the app

        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Calculator: Circle Area") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Circle image as an example; Source: https://curvebreakerstestprep.com/find-area-of-a-circle-formula-examples/

            Image(
                painter = painterResource(id = R.drawable.circle_area_with_measurements),
                contentDescription = "Circle",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(bottom = 20.dp)
            )

            OutlinedTextField(
                value = radiusInput,
                onValueChange = {
                    radiusInput = it
                    area = null
                },
                label = { Text("Enter Radius (in inches)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF9C27B0),
                    focusedLabelColor = Color(0xFF6A1B9A)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // Calculate button
                Button(
                    onClick = {
                        val radiusText = radiusInput.trim()
                        area = when {
                            radiusText.isEmpty() -> {
                                null
                            }
                            radiusText.toDoubleOrNull() == null -> {
                                null
                            }
                            radiusText.toDouble() < 0.0 -> {
                                null
                            }
                            else -> {
                                calculateCircleArea(radiusText.toDouble())
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37))
                ) {
                    Text("Calculate", color = Color.White)
                }

                // Exit button
                Button(
                    onClick = { exitApp(activity) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000))
                ) {
                    Text("Exit", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            when {
                // Area not null
                area != null ->
                    Text(
                    text = "Area = %.5f inches²".format(area),
                    color = Color(0xFF004D40),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )

                // Empty radius input not allowed
                radiusInput.isNotBlank() && radiusInput.toDoubleOrNull() == null ->
                    Text(
                    text = "Try Again. Radius Must Be A Valid Number.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )

                // Negative radius input not allowed
                radiusInput.toDoubleOrNull()?.let { it < 0.0 } == true ->
                    Text(
                    text = "Try Again. Radius Cannot Be A Negative Number.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

// Preview function

@Preview(showBackground = true)
@Composable
fun CircleAreaAppPreview() {
    CircleArea()
}