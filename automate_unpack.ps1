# PowerShell script to automate running XWorkshopUnpackGUI on multiple .bin files

# Prompt for the input directory
$inputDir = Read-Host "Enter the path to the directory containing folders with .bin files"

# Check if the directory exists
if (-not (Test-Path $inputDir)) {
    Write-Host "Directory does not exist: $inputDir"
    exit
}

# Get all subfolders that contain .bin files
$foldersWithBin = Get-ChildItem -Path $inputDir -Directory | Where-Object {
    Get-ChildItem -Path $_.FullName -Filter "*.bin" -File
}

# Counter for output folders
$counter = 1

# Path to the program
$programPath = Join-Path $PSScriptRoot "bin\XWorkshopUnpackGUI.bat"

# For each folder
foreach ($folder in $foldersWithBin) {
    # Find the .bin file (assuming one per folder)
    $binFile = Get-ChildItem -Path $folder.FullName -Filter "*.bin" -File | Select-Object -First 1
    if ($binFile) {
        # Create output folder
        $outputFolder = Join-Path $inputDir $counter.ToString()
        # Note: Don't create the directory here; let the program create it

        # Run the program in headless mode with input and output
        $result = & $programPath $binFile.FullName $outputFolder 2>&1
        if ($result) {
            Write-Host "Output for $($binFile.Name): $result"
        }

        $counter++
    }
}

Write-Host "Processing complete."