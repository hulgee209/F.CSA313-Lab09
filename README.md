# Лаб 9: Код хамралт — JaCoCo-гоор тестээ хөтлөх

**F.CSA313 — Программ хангамжийн чанарын баталгаа ба тест (2026)**

- **Нэр:** Э.Батхүлэг
- **Оюутны код:** B232270040

## Орчин

```
$ java -version
java version "23.0.2" 2025-01-21
Java(TM) SE Runtime Environment (build 23.0.2+7-58)
Java HotSpot(TM) 64-Bit Server VM (build 23.0.2+7-58, mixed mode, sharing)

$ mvn -version
Apache Maven 3.9.16 (2bdd9fddda4b155ebf8000e807eb73fd829a51d5)
Maven home: D:\apache-maven-3.9.16-bin\apache-maven-3.9.16
Java version: 23.0.2, vendor: Oracle Corporation, runtime: D:\java
Default locale: en_US, platform encoding: UTF-8
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
```

Ажиллуулах: `mvn verify` → тайлан `target/site/jacoco/index.html`.

---

## Хэсэг A: Happy path тестүүд ба эхний хэмжилт

Зөвхөн хэвийн урсгалын 5 тест бичсэн (`ScholarshipCalculatorTest`):
`letterGrade_95_isA`, `letterGrade_85_isB`, `gpa_95_85_is3_5`, `scholarship_full_100`, `scholarship_partial_70`.

Үр дүн (`results/mvn-verify-A.txt`: `Tests run: 5`, `results/jacoco-A.csv`):

| Хэмжүүр | Covered | Missed | Хамралт |
|---|---|---|---|
| Line | 15 | 10 | 15/25 = **60%** |
| Branch | 20 | 21 | 20/41 ≈ **48.8%** |

**Шар/улаан мөрүүдийн ажиглалт.** Шар ромботой мөрүүд нь бүх `if`-ийн хамгаалалтын нөхцөлүүд (`score < 0 || score > 100`, `scores == null || scores.length == 0`, `gpa < 0 || gpa > 4 || credits < 0`) — тэдгээрийн зөвхөн "худал" салаа явсан; мөн `score >= 80`, `hasViolation`, `gpa >= 3.8 && credits >= 15`, `gpa >= 3.4 && credits >= 12` нөхцөлүүдийн нэг тал, `switch`-ийн 5 салаанаас зөвхөн A, B хоёр нь хамрагдсан.
Улаан (огт хүрээгүй) мөрүүд нь гурван `throw new IllegalArgumentException` буюу алдааны замууд, мөн доод дүн/тэтгэлгийн салаанууд: `return "C"`, `return "D"`, `return "F"`, `case "C"`, `case "D"`, `return 50`, сүүлийн `return 0`.
Өөрөөр хэлбэл happy-path тестүүд зөвхөн "сайн оюутан"-ы замыг шалгаж, буруу оролт ба бага дүнгийн бүх логикийг огт ажиллуулаагүй байна.
